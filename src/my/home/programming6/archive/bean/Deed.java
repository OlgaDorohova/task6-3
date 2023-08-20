package my.home.programming6.archive.bean;

import java.io.Serializable;
import java.util.Objects;

public class Deed extends Entity{

	private static final long serialVersionUID = -1921439100361950128L;

	private String name;
	private String major;
	private String phoneNumber;
	private Address address = new Address();

	public Deed() {
	}

	public Deed(String name, String major, String phoneNumber, Address address) {
		this.name = name;
		this.major = major;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address adress) {
		this.address = adress;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, major, name, phoneNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Deed other = (Deed) obj;
		return Objects.equals(address, other.address) && Objects.equals(major, other.major)
				&& Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber);
	}

	@Override
	public String toString() {
		return "Deed [name=" + name + ", major=" + major + ", phoneNumber=" + phoneNumber + "," + address + "]";
	}

	public class Address implements Serializable {

		private static final long serialVersionUID = 1029186561007599259L;

		private String country;
		private String city;
		private String street;

		public Address() {
		}

		public Address(String country, String city, String street) {
			this.country = country;
			this.city = city;
			this.street = street;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(city, country, street);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Address other = (Address) obj;
			return Objects.equals(city, other.city) && Objects.equals(country, other.country)
					&& Objects.equals(street, other.street);
		}

		@Override
		public String toString() {
			return "Adress [country=" + country + ", city=" + city + ", street=" + street + "]";
		}

	}
}
