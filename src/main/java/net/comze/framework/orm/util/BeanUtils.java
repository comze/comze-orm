/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.comze.framework.orm.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.PropertyEditor;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:gkzhong@gmail.com">GK.ZHONG</a>
 * @since 3.0.0
 * @version BeanUtils.java 3.0.0 Jan 11, 2011 11:22:59 PM
 */
public abstract class BeanUtils {

	public static Object invoke(Object object, Method method, Object... args) {
		try {
			return method.invoke(object, args);
		} catch (IllegalArgumentException e) {
			throw new BeanAccessException("Invoke fail: indicate that '" + method.toGenericString() + "' has been passed an illegal or inappropriate argument '" + ArrayUtils.toString(args) + "'", e);
		} catch (IllegalAccessException e) {
			throw new BeanAccessException("Invoke fail: executing method '" + method.getName() + "' does not have access to the definition of the specified class '" + method.getDeclaringClass().getName() + "'", e);
		} catch (InvocationTargetException e) {
			throw new BeanAccessException("Invoke fail: internal error '" + method.toGenericString() + "', argument '" + ArrayUtils.toString(args) + "'", e);
		}
	}

	public static <T> T newInstance(Class<T> classType) {
		try {
			return classType.newInstance();
		} catch (InstantiationException e) {
			throw new BeanAccessException("Class instance fail: does not have access to the constructor definition of '" + classType.getName() + "'", e);
		} catch (IllegalAccessException e) {
			throw new BeanAccessException("Class instance fail: the '" + classType.getName() + "' represents an abstract class, an array class, a primitive type, or the class has no nullary constructor", e);
		}
	}

	public static <T> PropertyDescriptor[] getPropertyDescriptorArray(Class<T> requiredType) {
		try {
			return Introspector.getBeanInfo(requiredType).getPropertyDescriptors();
		} catch (IntrospectionException e) {
			throw new BeanAccessException("Introspector fail: the '" + requiredType.getName() + "' can not being able to map a string class name to a Class object, not being able to resolve a string method name, or specifying a method name that has the wrong type signature for its intended use", e);
		}
	}

	public static <T> Field getDeclaredField(Class<T> classType, String name) {
		try {
			return classType.getDeclaredField(name);
		} catch (SecurityException e) {
			throw new BeanAccessException("Get field fail: indicate a security violation to access the '" + classType.getName() + "', field '" + name + "'", e);
		} catch (NoSuchFieldException e) {
			if (classType.equals(Object.class)) {
//				throw new BeanAccessException("Get field fail: signals that the '" + classType.getName() + "' doesn't have a field of '" + name + "'", e);
				return (Field) null;
			} else {
				return getDeclaredField(classType.getSuperclass(), name);
			}
		}
	}

	public static <T> Method getDeclaredMethod(Class<T> classType, String name, Class<?>... parameterTypes) {
		try {
			return classType.getDeclaredMethod(name, parameterTypes);
		} catch (SecurityException e) {
			throw new BeanAccessException("Get method fail: indicate a security violation to access the '" + classType.getName() + "', method '" + name + "', parameter '" + Arrays.deepToString(parameterTypes) + "'", e);
		} catch (NoSuchMethodException e) {
			throw new BeanAccessException("Get method fail: signals that the '" + classType.getName() + "' doesn't have a method of '" + name + "', parameter '" + Arrays.deepToString(parameterTypes) + "'", e);
		}
	}

	public static <T> boolean containsDeclaredMethod(Class<T> classType, String name, Class<?>... parameterTypes) {
		try {
			return ObjectUtils.isNotNull(getDeclaredMethod(classType, name, parameterTypes));
		} catch (Exception e) {
			return false;
		}
	}

	public static PropertyEditor getPropertyEditor(AnnotatedElement annotatedElement) {
		if (ObjectUtils.isNotNull(annotatedElement)) {
			net.comze.framework.annotation.PropertyEditor propertyEditorAnnotation = annotatedElement.getAnnotation(net.comze.framework.annotation.PropertyEditor.class);
			if (ObjectUtils.isNotNull(propertyEditorAnnotation) && StringUtils.isNotEmpty(propertyEditorAnnotation.className())) {
				try {
					return (PropertyEditor) Class.forName(propertyEditorAnnotation.className()).newInstance();
				} catch (InstantiationException e) {
					throw new BeanAccessException("Class instance fail: does not have access to the constructor definition of '" + propertyEditorAnnotation.className() + "'", e);
				} catch (IllegalAccessException e) {
					throw new BeanAccessException("Class instance fail: the '" + propertyEditorAnnotation.className() + "' represents an abstract class, an array class, a primitive type, or the class has no nullary constructor", e);
				} catch (ClassNotFoundException e) {
					throw new BeanAccessException("Class instance fail: no definition for the class with the '" + propertyEditorAnnotation.className() + "' could be found", e);
				}
			}
		}
		return (PropertyEditor) null;
	}

	public static String getAttribute(AnnotatedElement annotatedElement) {
		if (ObjectUtils.isNotNull(annotatedElement)) {
			net.comze.framework.annotation.Attribute attributeAnnotation = annotatedElement.getAnnotation(net.comze.framework.annotation.Attribute.class);
			if (ObjectUtils.isNotNull(attributeAnnotation) && StringUtils.isNotEmpty(attributeAnnotation.name())) {
				return attributeAnnotation.name();
			}
		}
		return (String) null;
	}

	public static <T> Map<String, BeanProperty> getBeanPropertyMap(Class<T> requiredType) {
		Assert.notNull(requiredType, "Get bean property fail: argument '" + Class.class.getName() + ", requiredType' illegal");
		PropertyDescriptor[] propertyDescriptorArray = getPropertyDescriptorArray(requiredType);
		Map<String, BeanProperty> beanPropertyMap = new HashMap<String, BeanProperty>(propertyDescriptorArray.length);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptorArray) {
			if(ObjectUtils.isNull(propertyDescriptor)) {
				continue ;
			}
			Field field = getDeclaredField(requiredType, propertyDescriptor.getName());
			if(ObjectUtils.isNull(field)) {
				continue ;
			}
			
			BeanProperty beanProperty = new BeanProperty();
			beanProperty.setName(propertyDescriptor.getName());
			beanProperty.setType(propertyDescriptor.getPropertyType());
			beanProperty.setAttribute(getAttribute(field));
			beanProperty.setPropertyEditor(getPropertyEditor(field));
			// :~

			// handle method annotations
			Method writeMethod = propertyDescriptor.getWriteMethod();
			if(ObjectUtils.isNotNull(writeMethod)) {
				if (StringUtils.isEmpty(beanProperty.getAttribute())) {
					beanProperty.setAttribute(getAttribute(writeMethod));
				}
				if (ObjectUtils.isNull(beanProperty.getPropertyEditor())) {
					beanProperty.setPropertyEditor(getPropertyEditor(writeMethod));
				}
				beanProperty.setWriteMethod(writeMethod);
			}
			
			Method readMethod = propertyDescriptor.getReadMethod();
			if(ObjectUtils.isNotNull(readMethod)) {
				if (StringUtils.isEmpty(beanProperty.getAttribute())) {
					beanProperty.setAttribute(getAttribute(readMethod));
				}
				if (ObjectUtils.isNull(beanProperty.getPropertyEditor())) {
					beanProperty.setPropertyEditor(getPropertyEditor(readMethod));
				}
				beanProperty.setReadMethod(readMethod);
			}
			// :~

			if (StringUtils.isEmpty(beanProperty.getAttribute())) {
				beanProperty.setAttribute(propertyDescriptor.getName());
			}
			beanPropertyMap.put(beanProperty.getAttribute(), beanProperty);
		}
		return beanPropertyMap;
	}

}
