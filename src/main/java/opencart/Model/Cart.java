package opencart.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart extends BaseEntity{
    @OneToOne
    @PrimaryKeyJoinColumn(name = "customerId", foreignKey = @ForeignKey(name = "CART_FK_CUSTOMER"))
    private Customer customer;

    //27/7/20
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "carts")
    private Set<Product> products;

    @Column(name = "dateAdded")
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    private Date dateAdded;

    @Column(name = "quantity")
    private int quantity;

}