package com.examen.msrodriguezlaureano.domain.ports.on;

import com.examen.msrodriguezlaureano.domain.aggregates.response.ResponseReniec;

public interface RestReniecOut {

    ResponseReniec getInfoReniec(String numDoc);
}
