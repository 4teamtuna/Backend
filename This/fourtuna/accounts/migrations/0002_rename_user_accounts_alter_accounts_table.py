# Generated by Django 4.2.6 on 2023-11-13 22:00

from django.db import migrations


class Migration(migrations.Migration):
    dependencies = [
        ("accounts", "0001_initial"),
    ]

    operations = [
        migrations.RenameModel(
            old_name="user",
            new_name="accounts",
        ),
        migrations.AlterModelTable(
            name="accounts",
            table="user",
        ),
    ]
