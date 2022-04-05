package devicemanagerrestservice;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exceptions.DeviceNotFoundException;

@RestController
public class DeviceController {

	  private final DeviceRepository repository;

	  DeviceController(DeviceRepository repository) {
	    this.repository = repository;
	  }
	  
	  //Add new device
	  @PostMapping("/devices")
	  Device createNewDevice(@RequestBody Device newDevice) {		  
	    repository.save(newDevice);
	    return newDevice;
	  }
	  
	  //Get device by ID
	  @GetMapping("/devices/{id}")
	  Device getOneDevice(@PathVariable Long id) {	    
	    return repository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
	  }
	  
	 /** //List all devices
	  @GetMapping("/devices")
	  List<Device> getAllDevices() {
	    return repository.findAll();
	  }**/
	  
	  //Update device
	  @PutMapping("/devices/{id}")
	  Device updateDevice(@RequestBody Device updatedDevice, @PathVariable Long id) {
	    
	    return repository.findById(id)
	      .map(device -> {
	    	  device.setBrand(updatedDevice.getBrand());
	    	  device.setName(updatedDevice.getName());
	        return repository.save(device);
	      })
	      .orElseGet(() -> {
	    	  updatedDevice.setId(id);
	        return repository.save(updatedDevice);
	      });
	  }
	  
	  @PatchMapping("/devices/{id}")
	  Device patchDevice( @PathVariable Long id, @RequestParam String brand, @RequestParam String name) {
		  return repository.findById(id)
			      .map(device -> {
			    	  if(brand != null)
			    		  device.setBrand(brand);
			    	  if(name != null)
			    		  device.setName(name);
			        return repository.save(device);
			      })
			      .orElseThrow(() -> new DeviceNotFoundException(id));		  
	  }

	  //Delete device
	  @DeleteMapping("/devices/{id}")
	  void deleteDevice(@PathVariable Long id) {
	    repository.deleteById(id);
	  }
	  
	  //List all devices by brand
	  @GetMapping("/devices")
	  List<Device> getDevicesByBrand(@RequestParam String brand, @RequestParam String name) {		 
		  return repository.findByBrandIgnoreCaseOrNameIgnoreCaseContaining(brand, name);

	  }  
}