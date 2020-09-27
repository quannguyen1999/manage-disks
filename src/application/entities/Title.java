package application.entities;

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
@Table(name = "Title")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Title {
	@Id
	private String titleId;
	
	@Column(columnDefinition = "nvarchar(50)")
	private String name;
	
	private boolean status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryId")
	private Category category;

	public Title(String titleId) {
		super();
		this.titleId = titleId;
	}
	
	
}
