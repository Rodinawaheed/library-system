CREATE INDEX idx_loans_active_book_id
    ON loans(book_id)
    WHERE returned_at IS NULL;