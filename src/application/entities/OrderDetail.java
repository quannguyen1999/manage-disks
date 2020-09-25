package application.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
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
@Table(name = "orderDetail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetail {
	@Id
	private String orderDetailsId;
	
	@Column
	private int quantity;
	
	@Column
	private int totalAmmount;
	
	@ManyToOne
	@JoinColumn(name = "orderId", foreignKey = @ForeignKey)
	private Order order;
	
	
	@ManyToOne
	@JoinColumn(name = "titleId", foreignKey = @ForeignKey)
	private Title title;
}
