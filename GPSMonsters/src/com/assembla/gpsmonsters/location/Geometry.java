package com.assembla.gpsmonsters.location;

public class Geometry {
	private GSONLocation location;
	private String location_type;
	private GSONViewport viewport;
	public GSONLocation getLocation() {
		return location;
	}
	public void setLocation(GSONLocation location) {
		this.location = location;
	}
	public String getLocation_type() {
		return location_type;
	}
	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}
	public GSONViewport getViewport() {
		return viewport;
	}
	public void setViewport(GSONViewport viewport) {
		this.viewport = viewport;
	}
}