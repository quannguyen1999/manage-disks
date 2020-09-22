package application.entities;

import java.time.LocalDate;

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
@Table(name = "Category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
	@Id
	private String categoryId;
	
	@Column(columnDefinition = "nvarchar(500)")
	private String name;
	
	@Column(columnDefinition = "nvarchar(1000)")
	private String description;
	
	@Column
	private float price;
}
