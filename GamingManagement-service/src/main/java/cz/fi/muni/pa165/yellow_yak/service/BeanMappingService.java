package cz.fi.muni.pa165.yellow_yak.service;

import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;

/**
 * Dozer mapper interface
 *
 * @author matho
 */
public interface BeanMappingService {
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();
}
