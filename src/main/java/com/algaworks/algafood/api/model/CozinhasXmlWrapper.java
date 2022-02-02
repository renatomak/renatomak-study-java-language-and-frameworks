package com.algaworks.algafood.api.model;

import com.algaworks.algafood.domain.model.Cozinha;
import lombok.Data;
import lombok.NonNull;


import java.util.List;

@Data
public class CozinhasXmlWrapper {

    @NonNull
    private List<Cozinha> cozinhas;
}
