package edu.esoft.com.service.impl;

import edu.esoft.com.dto.BranchDTO;
import edu.esoft.com.entity.Branch;
import edu.esoft.com.repository.BranchRepository;
import edu.esoft.com.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepo;
    private final ModelMapper mapper;

    @Override
    public BranchDTO createBranch(BranchDTO dto) {
        Branch saved = branchRepo.save(mapper.map(dto, Branch.class));
        return mapper.map(saved, BranchDTO.class);
    }

    @Override
    public List<BranchDTO> getAllBranches() {
        return branchRepo.findAll()
                .stream()
                .map(branch -> mapper.map(branch, BranchDTO.class))
                .toList();
    }

    @Override
    public BranchDTO getBranchById(Integer id) {
        Branch branch = branchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));
        return mapper.map(branch, BranchDTO.class);
    }

    @Override
    public BranchDTO updateBranch(Integer id, BranchDTO dto) {
        Branch branch = branchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        // update fields
        branch.setName(dto.getName());
        branch.setAddress(dto.getAddress());
        branch.setEmail(dto.getEmail());
        branch.setTelNo(dto.getTelNo());

        Branch updated = branchRepo.save(branch);
        return mapper.map(updated, BranchDTO.class);
    }

    @Override
    public void deleteBranch(Integer id) {
        branchRepo.deleteById(id);
    }
}
