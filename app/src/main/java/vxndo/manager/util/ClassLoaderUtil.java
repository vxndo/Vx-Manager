package vxndo.manager.util;

import android.content.*;
import dalvik.system.*;
import java.lang.reflect.*;

public class ClassLoaderUtil {

	public static Object loadDex(Context context, String path, String className, String methodName, Object[] params) throws Exception {
		ClassLoader parent = context.getClass().getClassLoader();
		PathClassLoader loader = new PathClassLoader(path, parent);
		Class<?> cfr = loader.loadClass(className);
		Object instance = cfr.newInstance();
		Method[] methods = cfr.getMethods();
		Object obj = null;
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(methodName) &&
				method.getParameterCount() == params.length) {
				obj = method.invoke(instance, params);
			}
		} return obj;
	}
}
