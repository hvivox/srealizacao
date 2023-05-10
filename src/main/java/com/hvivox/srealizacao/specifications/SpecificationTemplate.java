package com.hvivox.srealizacao.specifications;


import com.hvivox.srealizacao.model.Folha;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
        @Spec(path = "foco", spec = Like.class),
        @Spec(path = "notadia", spec = Equal.class),
        @Spec(path = "status", spec = Equal.class)
    })
    public interface FolhaSpec extends Specification<Folha> {}


}
