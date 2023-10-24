package com.example.supplierservice.Services;

import com.example.supplierservice.Entity.Supplier;
import com.example.supplierservice.Repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SupplierServicesImpl implements ISupplierServices {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public Supplier addSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }

    @Override
    public List<Supplier> fetchSupplierList() {
        return supplierRepository.findAll();
    }

    @Override
    public Optional<Supplier> getSupplierById(Integer id) {
        return supplierRepository.findById(id);
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public byte[] generateSupplierListPDF() throws IOException {
        List<Supplier> suppliers = supplierRepository.findAll();

        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText(); // Begin text section
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Suppliers List");
            contentStream.endText(); // End text section

            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.beginText(); // Begin a new text section
            contentStream.newLineAtOffset(50, 680);

            for (Supplier supplier : suppliers) {
                contentStream.showText("Supplier ID: " + supplier.getIdSupllier());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Name: " + supplier.getName());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Email: " + supplier.getEmail());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Phone: " + supplier.getPhone());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Address: " + supplier.getAddress());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Product Type: " + supplier.getProductType());
                contentStream.newLineAtOffset(0, -40);
            }
            contentStream.endText(); // End the text section
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return baos.toByteArray();
    }

}
