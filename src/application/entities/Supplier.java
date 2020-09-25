package application.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Supplier")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Supplier {
	@Id
	private String supplierId;
	
	@Column
	private String phone;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String address;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String companyName;

	public Supplier(String supplierId) {
		super();
		this.supplierId = supplierId;
	}
	
	
}
