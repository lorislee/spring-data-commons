/*
 * Copyright 2014-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.mapping.model;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.IdentifierAccessor;
import org.springframework.data.mapping.context.SampleMappingContext;

/**
 * @author Oliver Gierke
 * @author Mark Paluch
 */
public class IdPropertyIdentifierAccessorUnitTests {

	SampleMappingContext mappingContext = new SampleMappingContext();

	@Test(expected = IllegalArgumentException.class) // DATACMNS-599
	public void rejectsEntityWithoutIdentifierProperty() {

		new IdPropertyIdentifierAccessor(mappingContext.getRequiredPersistentEntity(Sample.class), new Sample());
	}

	@Test(expected = IllegalArgumentException.class) // DATACMNS-599
	public void rejectsNullBean() {

		new IdPropertyIdentifierAccessor(mappingContext.getRequiredPersistentEntity(SampleWithId.class), null);
	}

	@Test // DATACMNS-599
	public void returnsIdentifierValue() {

		SampleWithId sample = new SampleWithId();
		sample.id = 1L;

		IdentifierAccessor accessor = new IdPropertyIdentifierAccessor(
				mappingContext.getRequiredPersistentEntity(SampleWithId.class), sample);

		assertThat(accessor.getIdentifier()).isEqualTo(sample.id);
	}

	static class Sample {}

	static class SampleWithId {
		@Id Long id;
	}
}
