# 🐞 Bug Report & Security Validation

This file documents bugs and basic security testing results during automated testing of the e-commerce website.

## ✅ Security Testing Summary

Performed input validation and injection testing:

- **Search Field**: tested with `<script>alert('XSS')</script>` and `' OR '1'='1` → no vulnerabilities found.
- **Checkout Form**: tested for SQL injection and XSS inputs → system handled inputs securely with proper sanitization.

**Result**: No XSS or SQL Injection vulnerabilities detected during UI-level testing.

---

## 🐛 Reported Bugs

| ID  | Title                      | Description                                           | Severity | Steps to Reproduce | Expected vs Actual |
|-----|----------------------------|-------------------------------------------------------|----------|---------------------|--------------------|
| 1   | Cart quantity not updating | When changing quantity in cart, total price stays same | Medium   | 1. Add item → 2. Change qty | Total price should update but doesn't |
---
and please check Excel file for All Test Cases and Bug Reports
[Drive](https://docs.google.com/spreadsheets/d/1uZV7iLE72e8lBXsiv79D_1v64PSQyiX6/edit?usp=drive_link&ouid=113430173013318065519&rtpof=true&sd=true)

📌 **Notes**: All bugs were reproducible and documented during testing on the Magento test site.
