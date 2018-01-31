package org.koenighotze.docuploadws;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.*;
import java.net.*;

import org.junit.*;
import org.junit.runner.*;
import org.koenighotze.docuploadws.config.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DocumentRepositoryConfiguration.class})
public class DocumentRepositoryTest {
    @Autowired
    private DocumentRespository documentRespository;

    @Before
    public void setup() throws IOException, URISyntaxException {
        documentRespository.clear();
        documentRespository.init();
    }

    @Test
    public void initialRepoSetup() {
        assertThat(documentRespository.getNumberOfDocuments(), is(2));
    }

    @Test
    public void fetch() {
        assertThat(documentRespository.getNumberOfDocuments(), is(2));
        DocumentElem elem = documentRespository
                .getDocument("CopyofHECTORGRAEME.pdf");
        assertThat(elem.getName(), is("CopyofHECTORGRAEME.pdf"));
    }

    @Test
    public void fetchFailure() {
        assertThat(documentRespository.getNumberOfDocuments(), is(2));
        DocumentElem elem = documentRespository.getDocument("notthere");
        assertThat(elem, is(nullValue()));
    }

    @Test
    public void store() {
        documentRespository.clear();
        assertThat(documentRespository.getNumberOfDocuments(), is(0));
        DocumentElem elem = new DocumentElem("aname", "foo".getBytes(), "bla");
        documentRespository.storeDocument(elem);
        assertThat(documentRespository.getNumberOfDocuments(), is(1));
    }

}
