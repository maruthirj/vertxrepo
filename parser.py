# Preconditions: variable file_name and name defined

with open(file_name) as phone_book:
	book = {r.split(",")[0]: r for r in phone_book }
			
ret_val = book[lookup_name]