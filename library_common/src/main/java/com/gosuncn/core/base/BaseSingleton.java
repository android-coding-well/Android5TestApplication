package com.gosuncn.core.base;

/**
 * 单例模式的高级写法<br>
 * 双重判断，防止并发
 * @author HWJ
 *
 * @param <T>
 */
public abstract class BaseSingleton <T>{
	 private T instance;

	    protected abstract T newInstance();

	    public final T getInstance() {
	        if (instance == null) {
	            synchronized (BaseSingleton.class) {
	                if (instance == null) {
	                    instance = newInstance();
	                }
	            }
	        }
	        return instance;
	    }
}
