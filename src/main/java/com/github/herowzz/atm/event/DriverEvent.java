package com.github.herowzz.atm.event;

import com.google.common.eventbus.EventBus;

/**
 * 驱动事件
 * @author wangzz
 */
public abstract class DriverEvent {

	/**
	 * 任务总线
	 */
	private static EventBus eventBus = new EventBus();

	/**
	 * 注册事件处理器
	 * @param listener 监听器
	 */
	public static void register(IDriverListener<?> listener) {
		eventBus.register(listener);
	}

	/**
	 * 触发同步事件
	 * @param event 事件对象
	 */
	public static void post(IEventObject eventObject) {
		eventBus.post(eventObject);
	}

}
