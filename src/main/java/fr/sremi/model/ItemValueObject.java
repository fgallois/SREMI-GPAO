package fr.sremi.model;

import java.util.Date;

public class ItemValueObject {
	private boolean selected;
	private int commandLine;
	private String itemReference;
	private String itemLabel;
	private String emplacement;
	private int quantity;
	private Date dueDate;
	private String version;
	
	public ItemValueObject(ItemCommand itemCommand) {
		super();
		this.selected = false;
		this.commandLine = itemCommand.getLine();
		this.itemReference = itemCommand.getItem().getReference();
		this.itemLabel = itemCommand.getItem().getDescription();
		this.emplacement = itemCommand.getItem().getEmplacement();
		this.quantity = itemCommand.getQuantity();
		this.dueDate = itemCommand.getDueDate();
		this.version = itemCommand.getVersion();
	}
	
	public int getCommandLine() {
		return commandLine;
	}
	public void setCommandLine(int commandLine) {
		this.commandLine = commandLine;
	}
	public String getItemReference() {
		return itemReference;
	}
	public void setItemReference(String itemReference) {
		this.itemReference = itemReference;
	}
	public String getItemLabel() {
		return itemLabel;
	}
	public void setItemLabel(String itemLabel) {
		this.itemLabel = itemLabel;
	}
	public String getEmplacement() {
		return emplacement;
	}
	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
