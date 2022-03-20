package devicemanagerrestservice;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private DeviceRepository dRepo;

	//Test create one device CHECK
	@Test
	public void testCreateDevice() throws Exception {
		Device newDevice = new Device("Nokia", "99");
		newDevice.setId(1L);

		Mockito.when(dRepo.save(newDevice)).thenReturn(newDevice);

		String url = "/devices";

		ObjectMapper objectMapper = new ObjectMapper();
		String inputJson = objectMapper.writeValueAsString(newDevice);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

		String jsonResponse = mvcResult.getResponse().getContentAsString();
		String expectedJsonResponse = objectMapper.writeValueAsString(newDevice);

		Assert.assertEquals(expectedJsonResponse, jsonResponse);

	}

	//Test get one device CHECK
	@Test
	public void getOneDevice() throws Exception {
		Device newDevice = new Device("Nokia", "99");
		Long deviceId = 1L;
		newDevice.setId(deviceId);

		Optional<Device> device = Optional.of(newDevice);
		Mockito.when(dRepo.findById(1L)).thenReturn(device);

		String url = "/devices/1";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();

		String expectedJsonResponse = objectMapper.writeValueAsString(device);
		Assert.assertEquals(expectedJsonResponse, jsonResponse);
	}

	//Get ALL devices CHECK
	@Test
	public void testListDevices() throws Exception {
		List<Device> listDevices = new ArrayList<Device>();
		listDevices.add(new Device("Nokia", "99"));
		listDevices.add(new Device("LG", "Gram"));
		listDevices.add(new Device("Samsung", "s10"));

		Mockito.when(dRepo.findAll()).thenReturn(listDevices);

		String url = "/devices";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();

		String expectedJsonResponse = objectMapper.writeValueAsString(listDevices);
		Assert.assertEquals(expectedJsonResponse, jsonResponse);
	}

	//Test update one device CHECK
	@Test
	public void testUpdateDevice() throws Exception {
		Device newDevice = new Device("Nokia", "99");
		newDevice.setId(1L);

		Mockito.when(dRepo.save(newDevice)).thenReturn(newDevice);

		String url = "/devices/1";

		String inputJson = objectMapper.writeValueAsString(newDevice);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);

		mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());	
	}



	//Test delete CHECK
	@Test
	public void deleteDevice() throws Exception {
		Long deviceId = 1L;

		Mockito.doNothing().when(dRepo).deleteById(deviceId);

		String url = "/devices/1";		

		mockMvc.perform(MockMvcRequestBuilders.delete(url)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(dRepo, times(1)).deleteById(deviceId);
	}	

	//Get ALL devices by brand CHECK
	@Test
	public void testSearchByBrand() throws Exception {
		List<Device> listDevices = new ArrayList<Device>();
		List<Device> resultDevices = new ArrayList<Device>();

		listDevices.add(new Device("Nokia", "99"));
		listDevices.add(new Device("LG", "Gram"));
		listDevices.add(new Device("Samsung", "s10"));

		resultDevices.add(listDevices.get(0));

		Mockito.when(dRepo.findByBrand("Nokia")).thenReturn(resultDevices);

		String url = "/devices/brand/?brand=Nokia";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		String jsonResponse = mvcResult.getResponse().getContentAsString();

		String expectedJsonResponse = objectMapper.writeValueAsString(resultDevices);
		Assert.assertEquals(expectedJsonResponse, jsonResponse);
	}
}