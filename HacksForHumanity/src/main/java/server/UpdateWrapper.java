package server;

public class UpdateWrapper {
	String idReceiver;
	String idSender;
	String eventName;
	String eventContent;
	
	public UpdateWrapper() {}
	
	public String getIdReceiver() {
		return idReceiver;
	}
	public String getIdSender() {
		return idSender;
	}
	public String getEventName() {
		return eventName;
	}
	public String getEventContent() {
		return eventContent;
	}
	public void setIdReceiver(String idReceiver) {
		this.idReceiver = idReceiver;
	}
	public void setIdSender(String idSender) {
		this.idSender = idSender;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public void SetEventContent(String eventContent) {
		this.eventContent = eventContent;
	}
}
