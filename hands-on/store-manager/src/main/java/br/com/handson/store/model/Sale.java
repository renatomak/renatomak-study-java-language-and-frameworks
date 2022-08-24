package br.com.handson.store.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sales")
public class Sale {

    @EmbeddedId
    private SalePK id = new SalePK();

    private Integer quantity;

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }



}
