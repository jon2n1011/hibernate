package ProyectoHinernate.DAO;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, Id extends Serializable> {

	public void persist(T entity);

	public void update(T entity);

	public void saveOrUpdate(T entity);
	public void save(T entity);
	public T findById(Id id);

	public void delete(Id entity);

	public List<T> findAll();

	public void deleteAll();
}