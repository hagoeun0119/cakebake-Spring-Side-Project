package springjpasideproject.cakebake.domain.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import springjpasideproject.cakebake.domain.Product;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    public void save(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    public Product findOne(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findByCategory(String category) {
        return em.createQuery("select p from Product p where p.category = :category", Product.class)
            .getResultList();}

    public List<Product> findAll() {
        return em.createQuery("select p from Product p", Product.class)
                .getResultList();
    }
}
