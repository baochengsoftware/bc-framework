package cn.bc.one2onetest;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration("classpath:spring-test.xml")
public class AddressServiceImplTest {
	protected AddressService addressService;

	@Autowired
	public void setAddressService(AddressService addressService) {
		this.addressService = addressService;
	}

	@Test
	public void testNothing() {
	}

	//@Test
	//@Rollback(false)
	public void testSaveWithNotNullPerson() {
		Address address = new Address();
		address.setAddress("New Delhi, Sector-3");
		
		Person person = new Person();
		person.setName("Vinod Kumar");
		address.setPerson(person);;

		addressService.save(address);
	}

	//@Test
	//@Rollback(false)
	public void testSaveWithNullPerson() {
		Address address = new Address();
		address.setAddress("dragon");

		addressService.save(address);
	}

	//@Test
	//@Rollback(false)
	public void testDelete() {
		addressService.delete(new Integer[]{3,4});
	}

	//@Test
	//@Rollback(false)
	public void testUpdate() {
		Address address = addressService.load(5);
		Assert.assertNotNull(address);
		address.setAddress("aaaa");
		address.getPerson().setName("bbbb");
		addressService.save(address);
	}
}
