-- Remove NOT NULL constraint from icon and role columns
ALTER TABLE champions ALTER COLUMN icon DROP NOT NULL;
ALTER TABLE champions ALTER COLUMN role DROP NOT NULL;

-- Verify changes
SELECT column_name, is_nullable 
FROM information_schema.columns 
WHERE table_name = 'champions' 
AND column_name IN ('icon', 'role');
