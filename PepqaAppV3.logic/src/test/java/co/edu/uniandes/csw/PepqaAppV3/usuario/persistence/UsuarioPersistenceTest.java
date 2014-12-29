/* ========================================================================
 * Copyright 2014 PepqaAppV3
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 PepqaAppV3

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * ========================================================================


Source generated by CrudMaker version 1.0.0.201411201032

*/

package co.edu.uniandes.csw.PepqaAppV3.usuario.persistence;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.*;


import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.dto.UsuarioPageDTO;
import co.edu.uniandes.csw.PepqaAppV3.usuario.logic.dto.UsuarioDTO;
import co.edu.uniandes.csw.PepqaAppV3.usuario.persistence.api.IUsuarioPersistence;
import co.edu.uniandes.csw.PepqaAppV3.usuario.persistence.entity.UsuarioEntity;
import co.edu.uniandes.csw.PepqaAppV3.usuario.persistence.converter.UsuarioConverter;
import static co.edu.uniandes.csw.PepqaAppV3.util._TestUtil.*;

@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(UsuarioPersistence.class.getPackage())
				.addPackage(UsuarioEntity.class.getPackage())
				.addPackage(IUsuarioPersistence.class.getPackage())
                .addPackage(UsuarioDTO.class.getPackage())
                .addPackage(UsuarioConverter.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IUsuarioPersistence usuarioPersistence;

	@PersistenceContext
	private EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void configTest() {
		System.out.println("em: " + em);
		try {
			utx.begin();
			clearData();
			insertData();
			utx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				utx.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void clearData() {
		em.createQuery("delete from UsuarioEntity").executeUpdate();
	}

	private List<UsuarioEntity> data=new ArrayList<UsuarioEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			UsuarioEntity entity=new UsuarioEntity();
			entity.setName(generateRandom(String.class));
			entity.setDescripcion(generateRandom(String.class));
			entity.setPassword(generateRandom(String.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createUsuarioTest(){
		UsuarioDTO dto=new UsuarioDTO();
		dto.setName(generateRandom(String.class));
		dto.setDescripcion(generateRandom(String.class));
		dto.setPassword(generateRandom(String.class));
		
		UsuarioDTO result=usuarioPersistence.createUsuario(dto);
		
		Assert.assertNotNull(result);
		
		UsuarioEntity entity=em.find(UsuarioEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());
		Assert.assertEquals(dto.getDescripcion(), entity.getDescripcion());
		Assert.assertEquals(dto.getPassword(), entity.getPassword());
	}
	
	@Test
	public void getUsuariosTest(){
		List<UsuarioDTO> list=usuarioPersistence.getUsuarios();
		Assert.assertEquals(list.size(), data.size());
        for(UsuarioDTO dto:list){
        	boolean found=false;
            for(UsuarioEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getUsuarioTest(){
		UsuarioEntity entity=data.get(0);
		UsuarioDTO dto=usuarioPersistence.getUsuario(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
		Assert.assertEquals(entity.getDescripcion(), dto.getDescripcion());
		Assert.assertEquals(entity.getPassword(), dto.getPassword());
        
	}
	
	@Test
	public void deleteUsuarioTest(){
		UsuarioEntity entity=data.get(0);
		usuarioPersistence.deleteUsuario(entity.getId());
        UsuarioEntity deleted=em.find(UsuarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updateUsuarioTest(){
		UsuarioEntity entity=data.get(0);
		
		UsuarioDTO dto=new UsuarioDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		dto.setDescripcion(generateRandom(String.class));
		dto.setPassword(generateRandom(String.class));
		
		
		usuarioPersistence.updateUsuario(dto);
		
		
		UsuarioEntity resp=em.find(UsuarioEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
		Assert.assertEquals(dto.getDescripcion(), resp.getDescripcion());	
		Assert.assertEquals(dto.getPassword(), resp.getPassword());	
	}
	
	@Test
	public void getUsuarioPaginationTest(){
		//Page 1
		UsuarioPageDTO dto1=usuarioPersistence.getUsuarios(1,2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
        //Page 2
        UsuarioPageDTO dto2=usuarioPersistence.getUsuarios(2,2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
        
        for(UsuarioDTO dto:dto1.getRecords()){
        	boolean found=false;	
            for(UsuarioEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(UsuarioDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(UsuarioEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
}