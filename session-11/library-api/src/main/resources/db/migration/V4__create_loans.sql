
CREATE TABLE loans (
                       id BIGSERIAL PRIMARY KEY,
                       book_id BIGINT NOT NULL REFERENCES books(id),
                       member_id BIGINT NOT NULL REFERENCES members(id),
                       borrowed_at TIMESTAMPTZ NOT NULL DEFAULT now(),
                       due_date DATE NOT NULL,
                       returned_at TIMESTAMPTZ
);

CREATE INDEX idx_loans_member_id ON loans(member_id);
