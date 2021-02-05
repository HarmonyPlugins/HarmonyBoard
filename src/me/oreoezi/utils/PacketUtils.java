package me.oreoezi.utils;
import java.lang.reflect.Field;
public class PacketUtils {
	public static Object getValue(Object instance, String name) {
		Object result = null;	
		try {
			Field field = instance.getClass().getDeclaredField(name);
			field.setAccessible(true);
			result = field.get(instance);		
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
	public static Object setValue(Object instance, String name, Object value) {	
		try {
			Field field = instance.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(instance, value);		
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		return instance;
	}
}
