package com.expressway.ftp.client.models;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Ajaxfan
 */
@Table(name = "st_analytical_tbl")
public class Analytical {
	@Id
	private String carImage;
	private String exitDate;
	private String imageLoaded;
	private String scaned;

	public String getCarImage() {
		return carImage;
	}

	public String getExitDate() {
		return exitDate;
	}

	public void setExitDate(String exitDate) {
		this.exitDate = exitDate;
	}

	public void setCarImage(String carImage) {
		this.carImage = carImage;
	}

	public String getImageLoaded() {
		return imageLoaded;
	}

	public void setImageLoaded(String imageLoaded) {
		this.imageLoaded = imageLoaded;
	}

	public String getScaned() {
		return scaned;
	}

	public void setScaned(String scaned) {
		this.scaned = scaned;
	}
}
