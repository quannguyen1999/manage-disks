package application.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Entity
@Table(name = "Customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String customerId;
	
	@Column
	private String name;
	
	@Column
	private String address;
	
	@Column
	private String phone;
	
	@Column
	private Boolean gender;
	
	@Column
	private LocalDate dateOfBirth;
	
	
}
