package springjpasideproject.cakebake.domain;

import static jakarta.persistence.FetchType.*;
import jakarta.persistence.*;
import lombok.*;
import springjpasideproject.cakebake.exception.NotEnoughStockException;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL)
    private List<BasketProduct> basketProducts = new ArrayList<>();

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private String name;
    private String ingredient;
    private String image;
    private int price;
    private int stockQuantity;

    public Product updateProduct(String name, String ingredient, String image, int price, int stockQuantity, Category category) {
        this.name = name;
        this.ingredient = ingredient;
        this.image = image;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        return this;
    }

    public void addStock(int quantity) { this.stockQuantity += quantity; }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more Stock");
        }
        this.stockQuantity = restStock;
    }
}
