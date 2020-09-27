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
@Table(name = "lateFees")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LateFee {
	@Id
	private String lateFeetId;
	
	@Column//(columnDefinition = "nvarchar(500)")
	private Float price;
	
	@Column
	private LocalDate datePay;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "billId")
	private Bill bill;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String content;
}
