ALTER TABLE owner_data ADD constraint UK_2w16cjbxaxset61r6hmqj1vgp
		unique (login);

ALTER TABLE git_hub_data ADD constraint UK_nye2m5h7wup1pao9qf7cpgh90
		unique (full_name);