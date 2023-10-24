package com.example.supplierservice.Controller;

import com.example.supplierservice.Entity.Supplier;
import com.example.supplierservice.Services.ISupplierServices;
import com.example.supplierservice.Services.SupplierServicesImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/supplierService")
@RestController
public class SupplierController {

    @Autowired
    private ISupplierServices supplierServices;

    @PostMapping("/addS")
    public Supplier addSupplier(@RequestBody Supplier supplier) {
        return supplierServices.addSupplier(supplier);
    }

    @GetMapping("/supplier")
    public List<Supplier> fetchSupplierList()
    {
        return supplierServices.fetchSupplierList();
    }


    @DeleteMapping("/{id}")
    public List<Supplier> delete(@PathVariable Integer id) {
        supplierServices.deleteSupplier(id);
        return supplierServices.fetchSupplierList();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        Optional<Supplier> existingSupplier = supplierServices.getSupplierById(id);
        if (existingSupplier.isPresent()) {
            supplier.setIdSupllier(id);
            supplierServices.updateSupplier(supplier);
            return ResponseEntity.ok(supplier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/generate")
    public ResponseEntity<byte[]> generatePdf() throws IOException {
        byte[] pdfBytes = supplierServices.generateSupplierListPDF();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "suppliers.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
