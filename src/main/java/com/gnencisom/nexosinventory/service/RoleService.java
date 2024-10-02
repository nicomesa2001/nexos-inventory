package com.gnencisom.nexosinventory.service;

import com.gnencisom.nexosinventory.model.Role;
import com.gnencisom.nexosinventory.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Obtiene todos los roles.
     *
     * @return Lista de roles.
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
