package devicemanagerrestservice;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeviceRepositoryBaseTest {

	@Autowired
	DeviceRepository dRepo;
	
	@Test
	public void testCreateDevice() {
		Device d = new Device();
		d.setId(1L);
		d.setBrand("Sony");
		d.setName("Playstation 5");
		dRepo.save(d);
		Assert.assertNotNull(dRepo.findById(1L).get());
	}
	
	@Test
	public void testSearchByBrand() {
		Device d = new Device();
		d.setId(1L);
		d.setBrand("LG");
		d.setName("12");
		dRepo.save(d);
		Device d1 = new Device();
		d1.setId(2L);
		d1.setBrand("LG");
		d1.setName("123");
		dRepo.save(d1);
		Assert.assertEquals(dRepo.findByBrand("LG").size(), 2);
	}
	
	@Test
	public void testSearchByBrandAndName() {
		Device d = new Device();
		d.setId(1L);
		d.setBrand("LG");
		d.setName("12");
		dRepo.save(d);
		Device d1 = new Device();
		d1.setId(2L);
		d1.setBrand("SL");
		d1.setName("113");
		dRepo.save(d1);
		Assert.assertEquals(dRepo.findByBrandIgnoreCaseOrNameIgnoreCaseContaining("LG", "12").size(), 1);
		Assert.assertEquals(dRepo.findByBrandIgnoreCaseOrNameIgnoreCaseContaining("S", "").size(), 2);
		Assert.assertEquals(dRepo.findByBrandIgnoreCaseOrNameIgnoreCaseContaining("OLA", "5").size(), 0);
		Assert.assertEquals(dRepo.findByBrandIgnoreCaseOrNameIgnoreCaseContaining("L", "1").size(), 2);

	}
}