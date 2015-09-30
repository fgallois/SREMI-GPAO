package fr.sremi.services;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import fr.sremi.dao.PartRepository;
import fr.sremi.data.PartData;
import fr.sremi.data.PartPaginationData;
import fr.sremi.model.Part;

/**
 * Created by fgallois on 9/28/15.
 */
@Component
public class CatalogService {

    @Resource
    private PartRepository partRepository;

    public PartPaginationData getPartsByReferencePaginated(final String reference, final int pageNumber,
            final int pageSize) {
        List<PartData> partDatas = new ArrayList<>();
        Page<Part> parts = partRepository.findByReferenceContaining(reference, new PageRequest(pageNumber, pageSize));

        for (Part part : parts) {
            PartData partData = new PartData(part.getReference(), part.getModification(), part.getIsPlanTableau(),
                    part.getDescription(), part.getComment());
            partDatas.add(partData);
        }
        return new PartPaginationData(partRepository.count(), partDatas);
    }

    public List<PartData> getAllParts() {
        List<PartData> result = new ArrayList<>();
        Iterable<Part> iterator = partRepository.findAll();

        for (Part part : iterator) {
            PartData partData = new PartData(part.getReference(), part.getModification(), part.getIsPlanTableau(),
                    part.getDescription(), part.getComment());
            result.add(partData);
        }
        return result;
    }
}
