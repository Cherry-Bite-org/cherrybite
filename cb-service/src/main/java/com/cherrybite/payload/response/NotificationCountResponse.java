package com.cherrybite.payload.response;

public class NotificationCountResponse {

    public NotificationCountResponse(Long count) {
		super();
		this.count = count;
	}

	private Long count;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
