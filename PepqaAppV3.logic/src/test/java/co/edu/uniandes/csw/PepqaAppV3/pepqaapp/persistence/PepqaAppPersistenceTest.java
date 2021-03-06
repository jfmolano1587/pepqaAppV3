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

package co.edu.uniandes.csw.PepqaAppV3.pepqaapp.persistence;

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


import co.edu.uniandes.csw.PepqaAppV3.pepqaapp.logic.dto.PepqaAppPageDTO;
import co.edu.uniandes.csw.PepqaAppV3.pepqaapp.logic.dto.PepqaAppDTO;
import co.edu.uniandes.csw.PepqaAppV3.pepqaapp.persistence.api.IPepqaAppPersistence;
import co.edu.uniandes.csw.PepqaAppV3.pepqaapp.persistence.entity.PepqaAppEntity;
import co.edu.uniandes.csw.PepqaAppV3.pepqaapp.persistence.converter.PepqaAppConverter;
import static co.edu.uniandes.csw.PepqaAppV3.util._TestUtil.*;

@RunWith(Arquillian.class)
public class PepqaAppPersistenceTest {

	public static final String DEPLOY = "Prueba";

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class, DEPLOY + ".war")
				.addPackage(PepqaAppPersistence.class.getPackage())
				.addPackage(PepqaAppEntity.class.getPackage())
				.addPackage(IPepqaAppPersistence.class.getPackage())
                .addPackage(PepqaAppDTO.class.getPackage())
                .addPackage(PepqaAppConverter.class.getPackage())
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("META-INF/beans.xml", "beans.xml");
	}

	@Inject
	private IPepqaAppPersistence pepqaAppPersistence;

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
		em.createQuery("delete from PepqaAppEntity").executeUpdate();
	}

	private List<PepqaAppEntity> data=new ArrayList<PepqaAppEntity>();

	private void insertData() {
		for(int i=0;i<3;i++){
			PepqaAppEntity entity=new PepqaAppEntity();
			entity.setName(generateRandom(String.class));
			em.persist(entity);
			data.add(entity);
		}
	}
	
	@Test
	public void createPepqaAppTest(){
		PepqaAppDTO dto=new PepqaAppDTO();
		dto.setName(generateRandom(String.class));
		
		PepqaAppDTO result=pepqaAppPersistence.createPepqaApp(dto);
		
		Assert.assertNotNull(result);
		
		PepqaAppEntity entity=em.find(PepqaAppEntity.class, result.getId());
		
		Assert.assertEquals(dto.getName(), entity.getName());
	}
	
	@Test
	public void getPepqaAppsTest(){
		List<PepqaAppDTO> list=pepqaAppPersistence.getPepqaApps();
		Assert.assertEquals(list.size(), data.size());
        for(PepqaAppDTO dto:list){
        	boolean found=false;
            for(PepqaAppEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
	@Test
	public void getPepqaAppTest(){
		PepqaAppEntity entity=data.get(0);
		PepqaAppDTO dto=pepqaAppPersistence.getPepqaApp(entity.getId());
        Assert.assertNotNull(dto);
		Assert.assertEquals(entity.getName(), dto.getName());
        
	}
	
	@Test
	public void deletePepqaAppTest(){
		PepqaAppEntity entity=data.get(0);
		pepqaAppPersistence.deletePepqaApp(entity.getId());
        PepqaAppEntity deleted=em.find(PepqaAppEntity.class, entity.getId());
        Assert.assertNull(deleted);
	}
	
	@Test
	public void updatePepqaAppTest(){
		PepqaAppEntity entity=data.get(0);
		
		PepqaAppDTO dto=new PepqaAppDTO();
		dto.setId(entity.getId());
		dto.setName(generateRandom(String.class));
		
		
		pepqaAppPersistence.updatePepqaApp(dto);
		
		
		PepqaAppEntity resp=em.find(PepqaAppEntity.class, entity.getId());
		
		Assert.assertEquals(dto.getName(), resp.getName());	
	}
	
	@Test
	public void getPepqaAppPaginationTest(){
		//Page 1
		PepqaAppPageDTO dto1=pepqaAppPersistence.getPepqaApps(1,2);
        Assert.assertNotNull(dto1);
        Assert.assertEquals(dto1.getRecords().size(),2);
        Assert.assertEquals(dto1.getTotalRecords().longValue(),3L);
        //Page 2
        PepqaAppPageDTO dto2=pepqaAppPersistence.getPepqaApps(2,2);
        Assert.assertNotNull(dto2);
        Assert.assertEquals(dto2.getRecords().size(),1);
        Assert.assertEquals(dto2.getTotalRecords().longValue(),3L);
        
        for(PepqaAppDTO dto:dto1.getRecords()){
        	boolean found=false;	
            for(PepqaAppEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
        
        for(PepqaAppDTO dto:dto2.getRecords()){
        	boolean found=false;
            for(PepqaAppEntity entity:data){
            	if(dto.getId().equals(entity.getId())){
                	found=true;
                }
            }
            Assert.assertTrue(found);
        }
	}
	
}