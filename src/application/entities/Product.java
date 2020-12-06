package application.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
	@Id
	private String productId;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String name;
	
	@Column(columnDefinition = "nvarchar(max)")
	private String picture;
	
	@Column//(columnDefinition = "nvarchar(1000)")
	private int quantity;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String description;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String status;
	
	@Column
	private LocalDate dateAdded;
	
	@ManyToOne//( fetch = FetchType.LAZY)
	@JoinColumn(name = "titleId")
	private Title title;
	
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplierId")
	private Supplier supplier;
	
	@Column//(columnDefinition = "nvarchar(1000)")
	private int quantityRentDisk;
	
	@Column//(columnDefinition = "nvarchar(1000)")
	private int quantityOnShelf;
	
	@Column//(columnDefinition = "nvarchar(1000)")
	private int quantityOnHold;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		Product other = (Product) obj;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		return true;
	}
	
	

}
