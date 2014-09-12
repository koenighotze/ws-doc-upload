package org.koenighotze.docuploadws;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.koenighotze.docuploadws.config.DocumentRepositoryConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DocumentRepositoryConfiguration.class })
public class DocumentRepositoryTest {
	@Autowired
	private DocumentRespository documentRespository;

	@Test
	public void testInitialRepoSetup() {
		assertThat(this.documentRespository.getNumberOfDocuments(), is(2));
	}

	@Test
	public void testFetch() {
		assertThat(this.documentRespository.getNumberOfDocuments(), is(2));
		DocumentElem elem = this.documentRespository
				.getDocument("CopyofHECTORGRAEME.pdf");
		assertThat(elem.getName(), is("CopyofHECTORGRAEME.pdf"));
	}

	@Test
	public void testFetchFailure() {
		assertThat(this.documentRespository.getNumberOfDocuments(), is(2));
		DocumentElem elem = this.documentRespository.getDocument("notthere");
		assertThat(elem, is(nullValue()));
	}

	@Test
	public void testStore() {
		this.documentRespository.clear();
		assertThat(this.documentRespository.getNumberOfDocuments(), is(0));
		DocumentElem elem = new DocumentElem("aname", "foo".getBytes(), "bla");
		this.documentRespository.storeDocument(elem);
		assertThat(this.documentRespository.getNumberOfDocuments(), is(1));
	}

}
