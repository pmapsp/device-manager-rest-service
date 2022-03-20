package devicemanagerrestservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Device {
	
	private @Id @GeneratedValue Long id;
	private String brand;
	private String name;
	private Long creationTime;

	Device(){
		creationTime = System.currentTimeMillis();
	}
	
	Device(String name, String brand){
		this.name = name;
		this.brand = brand;
		creationTime = System.currentTimeMillis();
	}
	
	Device(String name, String brand, Long Id){
		this.name = name;
		this.brand = brand;
		this.id = Id;
		creationTime = System.currentTimeMillis();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getCreationTime() {
		return creationTime;
	}	
}
