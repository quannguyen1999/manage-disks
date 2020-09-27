package application.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Bill")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bill {
	@Id
	private String billId;
	
	@Column//(columnDefinition = "nvarchar(500)")
	private LocalDate localDate;

	@Column
	private LocalDate billPay;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId")
	private Customer customer;
	
//	@OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
//	private List<LateFees> lateFees;
	
	@Column
	private boolean debt;
}
