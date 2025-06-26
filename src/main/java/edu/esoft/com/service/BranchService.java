package edu.esoft.com.service;

import edu.esoft.com.dto.BranchDTO;

import java.util.List;

public interface BranchService {

    BranchDTO createBranch(BranchDTO dto);
    List<BranchDTO> getAllBranches();
    BranchDTO getBranchById(Integer id);
    BranchDTO updateBranch(Integer id, BranchDTO dto);
    void deleteBranch(Integer id);
}
