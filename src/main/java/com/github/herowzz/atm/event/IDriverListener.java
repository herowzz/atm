package com.github.herowzz.atm.event;

/**
 * 监听器接口
 * @author wangzz
 */
public interface IDriverListener<T extends IEventObject> {

	/**
	 * 监听对象
	 * @param eventObject 需要监听的对象
	 */
	public void listen(T eventObject);

}
