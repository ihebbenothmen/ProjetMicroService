package com.example.supplierservice.Services;

import com.example.supplierservice.Entity.Supplier;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ISupplierServices {

    Supplier addSupplier(Supplier supplier);

    void deleteSupplier(Integer id);

    List<Supplier> fetchSupplierList();

    Optional<Supplier> getSupplierById(Integer id);

    void updateSupplier(Supplier supplier);

    byte[] generateSupplierListPDF() throws IOException;
}
