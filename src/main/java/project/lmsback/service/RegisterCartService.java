package project.lmsback.service;

import project.lmsback.domain.RegisterCartDTO;

import java.util.List;

public interface RegisterCartService {
    void saveCartPriorities(List<RegisterCartDTO> priorityList);

    List<RegisterCartDTO> getCartDTOListByStdtId(Integer stdtId);
}
